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

package plugin.go.skeleton;

import com.thoughtworks.go.plugin.api.material.packagerepository.PackageRevision;

import java.text.SimpleDateFormat;
import java.util.*;

import static utils.Constants.PACKAGE_CONFIGURATION;
import static utils.Constants.REPOSITORY_CONFIGURATION;

public class PackagePoller {
    private ConnectionHandler connectionHandler;

    public PackagePoller(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public Map handleCheckPackageConnection(Map requestBodyMap) {
        Map response = new HashMap();
        List messages = new ArrayList();

        //Skeleton should use to connection handler to check connection and get a revision
        String revision = "3.5.1";
        messages.add("Successfully found revision: " + revision);
        response.put("status", "success");
        response.put("messages", messages);

        return response;
    }

    public Map handleLatestRevision(Map request) {
        return pollForRevision(request, "");
    }

    public Map handleLatestRevisionSince(Map request) {
        Map revisionMap = (Map) request.get("previous-revision");
        Map data = (Map) revisionMap.get("data");
        String previousVersion = (String) data.get("SKELETON_VERSION");

        return pollForRevision(request, previousVersion);
    }

    private Map pollForRevision(Map request, String knownPackageRevision) {
        // Use the Connection Handler to get the collection of data
        Map repoConfigMap = (Map) request.get(REPOSITORY_CONFIGURATION);

        String repoUrl = parseValueFromEmbeddedMap(repoConfigMap, "SKELETON_REPOSITORY_URL");

        Map packageConfigMap = (Map) request.get(PACKAGE_CONFIGURATION);
        String packageId = parseValueFromEmbeddedMap(packageConfigMap, "SKELETON_PACKAGE_ID");

        //Skeleton: use information to get url (possibly with credentials)
        //You can use information from the previous verison's data object (like knownPackageRevision) to query for a newer revision
        String url = repoUrl + "/" + packageId + "?previousVersion=" + knownPackageRevision;

        return parsePackageDataFromDocument(url);
    }

    private Map parsePackageDataFromDocument(String url) {
        //Skeleton: use url to get package revision
        PackageRevision packageRevision = null;

        Map packageRevisionMap = new HashMap();
        packageRevisionMap.put("revision", packageRevision.getRevision());
        packageRevisionMap.put("timestamp", formatTimestamp(packageRevision.getTimestamp()));
        packageRevisionMap.put("user", packageRevision.getUser());
        packageRevisionMap.put("revisionComment", packageRevision.getRevisionComment());
        packageRevisionMap.put("trackbackUrl", packageRevision.getTrackbackUrl());
        packageRevisionMap.put("data", packageRevision.getData());

        return packageRevisionMap;
    }

    private String formatTimestamp(Date timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String date = dateFormat.format(timestamp);
        return date;
    }

    private String parseValueFromEmbeddedMap(Map configMap, String fieldName) {
        if (configMap.get(fieldName) == null) return "";

        Map fieldMap = (Map) configMap.get(fieldName);
        String value = (String) fieldMap.get("value");
        return value;
    }

}
