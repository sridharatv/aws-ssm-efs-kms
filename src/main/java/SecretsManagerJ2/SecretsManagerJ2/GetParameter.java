package SecretsManagerJ2.SecretsManagerJ2;

/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * This file is licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License. A copy of
 * the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 */


import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

public class GetParameter {

    public static void main(String[] args) {

/*        final String USAGE = "\n" +
                "Usage:\n" +
                "    GetParameter <paraName>\n\n" +
                "Where:\n" +
                "    paraName - The name of the parameter.\n";

        if (args.length < 1) {
            System.out.println(USAGE);
            System.exit(1);
        }*/

        /* Read the name from command args */
        //String paraName = args[0];
    	// String paraName = "encryption_key";
    	String paraName = "/aws/reference/secretsmanager/dev/demo/db01";
    	
        Region region = Region.AP_SOUTH_1;
        SsmClient ssmClient = SsmClient.builder()
                .region(region)
                .build();

        getParaValue(ssmClient, paraName);
    }

    public static void getParaValue(SsmClient ssmClient, String paraName) {

        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                .name(paraName)
                .withDecryption(true)
                .build();

            GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
            System.out.println("The parameter value is " + parameterResponse.parameter().value());

        } catch (SsmException e) {
        System.err.println(e.getMessage());
        System.exit(1);
        }
   }
}
