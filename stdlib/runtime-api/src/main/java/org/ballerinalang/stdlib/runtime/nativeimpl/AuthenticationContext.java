/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.stdlib.runtime.nativeimpl;

import org.ballerinalang.jvm.values.MapValue;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BValue;

/**
 * AuthenticationContext represents and holds the authentication information.
 *
 * @since 0.970.0
 */
public class AuthenticationContext {

    private static final String AUTH_SCHEME_KEY = "scheme";
    private static final String AUTH_TOKEN_KEY = "authToken";
    private MapValue<String, Object> authenticationContextRecord = null;
    private BMap<String, BValue> authenticationContextStruct = null;

    //TODO Remove after migration : implemented using bvm values/types
    public AuthenticationContext(BMap<String, BValue> authContextStruct) {
        this.authenticationContextStruct = authContextStruct;
    }

    public AuthenticationContext(MapValue<String, Object> authContextRecord) {
        this.authenticationContextRecord = authContextRecord;
    }

    public String getScheme() {
        //TODO this check is to distinguish both bvm and jvm values. Remove after the migration
        if (authenticationContextStruct != null) {
            return authenticationContextStruct.get(AUTH_SCHEME_KEY).stringValue();
        }
        return authenticationContextRecord.get(AUTH_SCHEME_KEY).toString();
    }

    public void setScheme(String authType) {
        //TODO this check is to distinguish both bvm and jvm values. Remove after the migration
        if (authenticationContextStruct != null) {
            authenticationContextStruct.put(AUTH_SCHEME_KEY, new BString(authType));
        } else {
            authenticationContextRecord.put(AUTH_SCHEME_KEY, authType);

        }
    }

    public String getAuthToken() {
        //TODO this check is to distinguish both bvm and jvm values. Remove after the migration
        if (authenticationContextStruct != null) {
            return authenticationContextStruct.get(AUTH_TOKEN_KEY).stringValue();
        }
        return authenticationContextRecord.get(AUTH_TOKEN_KEY).toString();
    }

    public void setAuthToken(String authToken) {
        //TODO this check is to distinguish both bvm and jvm values. Remove after the migration
        if (authenticationContextStruct != null) {
            authenticationContextStruct.put(AUTH_TOKEN_KEY, new BString(authToken));
        } else {
            authenticationContextRecord.put(AUTH_TOKEN_KEY, authToken);
        }
    }
}
