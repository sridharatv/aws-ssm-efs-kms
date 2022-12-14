package SecretsManagerJ2.SecretsManagerJ2;

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.DescribeSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.DescribeSecretResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DescribeSecret {

    public static void main(String[] args) {

        /*final String USAGE = "\n" +
                "To run this example, supply the name of the secret to describe (for example, tutorials/MyFirstSecret).  \n" +
                "\n" +
                "Example: DescribeSecret <secretName>\n";

        if (args.length < 1) {
            System.out.println(USAGE);
            System.exit(1);
        }
*/
        /* Read the name from command args */
        // String secretName = args[0];
    	String secretName = "dbpaswd";

        Region region = Region.AP_SOUTH_1;
        SecretsManagerClient secretsClient = SecretsManagerClient.builder()
                .region(region)
                .build();

        describeGivenSecret(secretsClient, secretName);
    }

    public static void describeGivenSecret(SecretsManagerClient secretsClient, String secretName) {

        try {
            DescribeSecretRequest secretRequest = DescribeSecretRequest.builder()
                .secretId(secretName)
                .build();

            DescribeSecretResponse secretResponse = secretsClient.describeSecret(secretRequest);
            Instant lastChangedDate = secretResponse.lastChangedDate();

            // Convert the Instant to readable date
            DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.US)
                        .withZone( ZoneId.systemDefault() );

            formatter.format( lastChangedDate );
            System.out.println ("Secret Details: " + secretResponse);
            System.out.println("The date of the last change to "+ secretResponse.name() +" is " + lastChangedDate );
        } catch (SecretsManagerException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
