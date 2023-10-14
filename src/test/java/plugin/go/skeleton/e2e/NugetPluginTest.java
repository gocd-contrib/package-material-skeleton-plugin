/*
 * Copyright 2023 Thoughtworks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package plugin.go.skeleton.e2e;


import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plugin.go.skeleton.SkeletonController;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utils.Constants.REPOSITORY_CONFIGURATION;
import static utils.Constants.SUCCESS_RESPONSE_CODE;

public class NugetPluginTest {

    SkeletonController skeletonController;
    GoPluginApiRequest goApiPluginRequest;


    @BeforeEach
    public void setUp() {
        skeletonController = new SkeletonController();
        goApiPluginRequest = mock(GoPluginApiRequest.class);

    }

    @Test
    public void shouldReturnConfigurationsWhenHandlingRepositoryConfigurationRequest() {
        String expectedRepositoryConfiguration = "{\"SKELETON_PASSWORD\":{\"display-order\":\"2\",\"display-name\":\"Skeleton Password\",\"part-of-identity\":false,\"secure\":true,\"required\":false}," +
                "\"SKELETON_USERNAME\":{\"display-order\":\"1\",\"display-name\":\"Skeleton Username\",\"part-of-identity\":false,\"secure\":false,\"required\":false}," +
                "\"SKELETON_REPOSITORY_URL\":{\"display-order\":\"0\",\"display-name\":\"Skeleton Repository Url\",\"part-of-identity\":true,\"secure\":false,\"required\":true}}";

        Map expectedRepositoryConfigurationMap = (Map) new GsonBuilder().create().fromJson(expectedRepositoryConfiguration, Object.class);
        when(goApiPluginRequest.requestName()).thenReturn(REPOSITORY_CONFIGURATION);

        GoPluginApiResponse response = skeletonController.handle(goApiPluginRequest);
        Map responseBodyMap = (Map) new GsonBuilder().create().fromJson(response.responseBody(), Object.class);

        assertEquals(SUCCESS_RESPONSE_CODE, response.responseCode());
        assertEquals(expectedRepositoryConfigurationMap, responseBodyMap);
    }
}
