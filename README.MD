Nexus is an AWS-GUI project focused on managing AWS resources (S3, DynamoDB, Cloudwatch...) and creating custom reports depending on these resources. Nexus doesn't have any user management system but have default authorization (root, root) which can be changed from application.properties.

<h3>AWS Credentials</h3>
If you have credentials under .aws folder in your root, this project will automatically work. If you don't have any credentials you'll need to edit the following file:

```
nano ~/.aws/credentials
```

<h3>Running the Project</h3>
You can run the project with mvn. Project will start at port 8080. If this port is not available you'll need to change it.

```
mvn spring-boot:run
```


