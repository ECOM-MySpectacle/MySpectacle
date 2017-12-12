#!/bin/sh

# Inspired from https://github.com/circleci/go-ecs-ecr
# more bash-friendly output for jq
JQ="jq --raw-output --exit-status"

configure_aws_cli(){
	aws --version
	aws configure set default.region eu-west-2
	aws configure set default.output json
}

deploy_cluster() {

    family="myspectacle-webapp-task"
    cluster="myspectacle-cluster"
    service="myspectacle-service"

    make_task_def
    register_definition
    if [[ $(aws ecs update-service --cluster $cluster --service $service --task-definition $revision | \
                   $JQ '.service.taskDefinition') != $revision ]]; then
        echo "Error updating service."
        return 1
    fi

    # wait for older revisions to disappear
    for attempt in $(seq 1 30); do
        if stale=$(aws ecs describe-services --cluster $cluster --services $service | \
                       $JQ ".services[0].deployments | .[] | select(.taskDefinition != \"$revision\") | .taskDefinition"); then
            echo "Waiting for stale deployments:"
            echo "$stale"
            sleep 5
        else
            echo "Deployed!"
            return 0
        fi
    done
    echo "Service update took too long."
    return 1
}

make_task_def(){
	task_template='[
		{
			"name": "myspectacle-container",
			"image": "%s.dkr.ecr.eu-west-2.amazonaws.com/myspectacle:%s",
			"essential": true,
			"memory": 990,
			"cpu": 1,
			"portMappings": [
				{
					"containerPort": 8080,
					"hostPort": 80,
			        "protocol": "tcp"
				}
            ],
            "mountPoints": [
                {
                "readOnly": false,
                "containerPath": "/var/lib/mysql",
                "sourceVolume": "persistance"
                }
            ],
            "environment": [
                {
                    "name": "WILDFLY_USER",
                    "value": "%s"
                },
                {
                    "name": "WILDFLY_PASS",
                    "value": "%s"
                },
                {
                    "name": "DB_NAME",
                    "value": "%s"
                },
                {
                    "name": "DB_USER",
                    "value": "%s"
                },
                {
                    "name": "DB_PASS",
                    "value": "%s"
                },
                {
                    "name": "MYSQL_DATABASE",
                    "value": "%s"
                },
                {
                    "name": "MYSQL_USER",
                    "value": "%s"
                },
                {
                    "name": "MYSQL_PASSWORD",
                    "value": "%s"
                },
                {
                    "name": "MYSQL_ROOT_PASSWORD",
                    "value": "%s"
                }
            ]
		}
	]'
	
	task_def=$(printf "$task_template" $AWS_ACCOUNT_ID $CIRCLE_SHA1 \
                $WILDFLY_USER $WILDFLY_PASSWORD $DB_NAME $DB_USER $DB_PASSWORD \
                $DB_NAME $DB_USER $DB_PASSWORD $DB_ROOT_PASSWORD)

    volumes=$(printf '[{"name": "persistance","host": {"sourcePath": "/var/lib/mysql"}}]')
}

push_ecr_image(){
	eval $(aws ecr get-login --no-include-email --region eu-west-2)
	docker push $AWS_ACCOUNT_ID.dkr.ecr.eu-west-2.amazonaws.com/myspectacle:$CIRCLE_SHA1
}

register_definition() {

    if revision=$(aws ecs register-task-definition --container-definitions "$task_def" --volumes "$volumes" --family $family | $JQ '.taskDefinition.taskDefinitionArn'); then
        echo "Revision: $revision"
    else
        echo "Failed to register task definition"
        return 1
    fi

}

configure_aws_cli
push_ecr_image
deploy_cluster
