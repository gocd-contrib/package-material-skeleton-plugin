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

package plugin.go.skeleton.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plugin.go.skeleton.ConnectionHandler;
import plugin.go.skeleton.RepositoryConfigHandler;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static utils.Constants.REPOSITORY_CONFIGURATION;

public class RepositoryConfigHandlerTest {
    RepositoryConfigHandler repositoryConfigHandler;
    ConnectionHandler connectionHandler;

    @BeforeEach
    public void setUp() {
        connectionHandler = mock(ConnectionHandler.class);
        repositoryConfigHandler = new RepositoryConfigHandler(connectionHandler);
    }

    @Test
    public void shouldUseConnectionHandlerToCheckConnection() {
        String SOME_URL = "http://www.skeleton.com/";
        String SOME_USERNAME = "SomeUsername";
        String SOME_PASSWORD = "somePassword";

        repositoryConfigHandler.handleCheckRepositoryConnection(createUrlRequestBody(SOME_URL, SOME_USERNAME, SOME_PASSWORD));

        verify(connectionHandler).checkConnectionToRepo(SOME_URL, SOME_USERNAME, SOME_PASSWORD);
    }

    private Map createUrlRequestBody(String url, String username, String password) {
        Map urlMap = new HashMap();
        urlMap.put("value", url);
        Map fieldsMap = new HashMap();
        fieldsMap.put("SKELETON_REPOSITORY_URL", urlMap);
        Map usernameMap = new HashMap();
        usernameMap.put("value", username);
        fieldsMap.put("SKELETON_USERNAME", usernameMap);
        Map passwordMap = new HashMap();
        passwordMap.put("value", password);
        fieldsMap.put("SKELETON_PASSWORD", passwordMap);
        Map bodyMap = new HashMap();
        bodyMap.put(REPOSITORY_CONFIGURATION, fieldsMap);
        return bodyMap;
    }


}