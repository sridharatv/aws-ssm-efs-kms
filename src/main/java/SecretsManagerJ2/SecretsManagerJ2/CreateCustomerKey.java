package SecretsManagerJ2.SecretsManagerJ2;

/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.*
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
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.CreateAliasRequest;
import software.amazon.awssdk.services.kms.model.CreateKeyRequest;
import software.amazon.awssdk.services.kms.model.CreateKeyResponse;
import software.amazon.awssdk.services.kms.model.CustomerMasterKeySpec;
import software.amazon.awssdk.services.kms.model.KmsException;
public class CreateCustomerKey {

    public static void main(String[] args) {

        Region region = Region.AP_SOUTH_1;
        KmsClient kmsClient = KmsClient.builder()
                .region(region)
                .build();

       String keyDesc = "Created by the AWS KMS API";
       System.out.println("The key ID is "+createKey(kmsClient, keyDesc));
    }

    public static void createCustomAlias(KmsClient kmsClient, String targetKeyId, String aliasName) {

        try {
            CreateAliasRequest aliasRequest = CreateAliasRequest.builder()
                .aliasName(aliasName)
                .targetKeyId(targetKeyId)
                .build();

            kmsClient.createAlias(aliasRequest);

        } catch (KmsException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
    
    public static String createKey(KmsClient kmsClient, String keyDesc) {
    	String aliasName = "alias/dev/ProjectEnc"; 

        try {
        CreateKeyRequest keyRequest = CreateKeyRequest.builder()
                .description(keyDesc)
                .customerMasterKeySpec(CustomerMasterKeySpec.SYMMETRIC_DEFAULT)
                .keyUsage("ENCRYPT_DECRYPT")
                .build();

        CreateKeyResponse result = kmsClient.createKey(keyRequest);

        System.out.printf(
                "Created a customer key with ID \"%s\"%n",
                result.keyMetadata().arn());

        createCustomAlias(kmsClient, result.keyMetadata().keyId(), aliasName);

        return result.keyMetadata().keyId();
    } catch (KmsException e) {
        System.err.println(e.getMessage());
        System.exit(1);
    }
    return "";
    }
}
