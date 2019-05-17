# aws-lambda-hello-scala

A Hello, World app for AWS Lambda in Scala. This is the complementary example project 
for the [Zero to AWS Lambda in Scala](https://www.bks2.com/2019/05/02/hello-scala-aws-lambda/)
blog post.


## Requirements

This example project uses SBT for building Scala projects and Amazon's SAM CLI app
for testing, packaging, and deploying AWS Lambda functions. You'll need all of the following to complete the tutorial:

1. [SBT](https://www.scala-sbt.org/) • The Scala Build Tool
1. [SAM CLI](https://aws.amazon.com/serverless/sam/) • The AWS Serverless Application Model app (a wrapper around AWS CloudFormation and the CLI)
1. [AWS CLI](https://aws.amazon.com/cli/) • The popular CLI for accessing the AWS API
1. [Docker](https://www.docker.com/) • Required by SAM for local testing of Lambda functions
1. curl • The performant web client used to test the running Lambda functions
1. An AWS account and valid credentials

## Building and Running The Function Locally

Open your terminal to the project directory and run the following to build and run the function locally:

    $ sbt assembly && sam local start-api

The `sbt assembly` command to compile the function and package it in a "fat" JAR file could be run by itself, but since `sam local` depends on this step it's easier and clearer to combine them and then fail immediately if there is a compilation problem.

Open a separate terminal in the same directory and test out the function with `curl`:

    $ curl http://127.0.0.1:3000/hello/developer
    Hello, developer

The `Hello, $name` response from the function appears, with the name taken from the path parameter following the `hello/` segment. 

