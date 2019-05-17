#!env zsh

set -e

TIMEFMT=%E
CURL_ITERATIONS=5
MEMORY_SIZES=(128 256 384 512 768 1024 1536 2048 2560 3008)

# Returns a stack named with the memory size in use
memToStack() { mem=$1 && echo "lambdatest-$mem" }

# Deploys the lambda function with the given memory size (also renames function & package file to avoid conflicts)
deployWithMemSize() {
    mem=$1 
    template="packaged_$mem.yaml"
    < packaged.yaml sed "s/MemorySize: .*$/MemorySize: $mem/;s/HelloScalaFunction:/HelloScalaFunction$mem:/" > $template
    nohup sam deploy --template-file $template --stack-name $(memToStack $mem) --capabilities CAPABILITY_IAM & 
}

# Undeploys the lambda function with the given memory size
undeployWithMemSize() { mem=$1 && aws cloudformation delete-stack --stack-name $(memToStack $mem) }

# For each output url we've generated in a cloudformation stack, test the url repeatedly and print its stack name and performance 
timeAllStacks() {
    stacks=$(
        aws cloudformation describe-stacks | 
        jq -r '.Stacks[] | "\(.StackName) \(.Outputs[0].OutputValue)"' | 
        grep -v null | sed 's/{name}/developer/'
    )
    echo $stacks | while read -r stack url; do
        for i in {1..$CURL_ITERATIONS}; do
            echo -n "curl $stack $i " && time curl -s -o /dev/null $url
        done
    done
}

# Deploy all the memory-sized lambda functions and wait for them to be ready
for mem in $MEMORY_SIZES; do deployWithMemSize $mem; done
wait

# Test them all
echo "now testing"
timeAllStacks

# Undeploy all the lambdas
echo "undeploying"
for mem in $MEMORY_SIZES; do undeployWithMemSize $mem; done


