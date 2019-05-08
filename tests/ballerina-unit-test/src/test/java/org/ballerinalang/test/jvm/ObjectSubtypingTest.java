/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.ballerinalang.test.jvm;

import org.ballerinalang.jvm.util.exceptions.BLangRuntimeException;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.test.util.BCompileUtil;
import org.ballerinalang.test.util.BRunUtil;
import org.ballerinalang.test.util.CompileResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ObjectSubtypingTest {
    private CompileResult compileResult;

    @BeforeClass
    public void setup() {
        compileResult = BCompileUtil.compile("test-src/jvm/objects_subtyping.bal");
    }

    @Test
    public void testAdditionalMethodsInSourceType() {
        BValue[] result = BRunUtil.invoke(compileResult, "testAdditionalMethodsInSourceType");
        Assert.assertEquals(result[0].stringValue(), "{name:\"John Doe\", age:25}");
    }

    @Test(expectedExceptions = BLangRuntimeException.class,
          expectedExceptionsMessageRegExp = ".*incompatible types: 'Person1' cannot be cast to 'Employee1'")
    public void testCastingRuntimeError() {
        BRunUtil.invoke(compileResult, "testCastingRuntimeError");
    }
}
