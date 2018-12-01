/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.model.util.serializer.providers.bvalue;

import org.ballerinalang.model.types.BArrayType;
import org.ballerinalang.model.types.BType;
import org.ballerinalang.model.types.BTypes;
import org.ballerinalang.model.types.TypeTags;
import org.ballerinalang.model.util.serializer.BPacket;
import org.ballerinalang.model.util.serializer.BValueDeserializer;
import org.ballerinalang.model.util.serializer.BValueSerializer;
import org.ballerinalang.model.util.serializer.SerializationBValueProvider;
import org.ballerinalang.model.values.BRefType;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.model.values.BValueArray;

/**
 * Provide mapping between {@link BValueArray} and {@link BValue} representation of it.
 *
 * @since 0.983.0
 */
public class BValueArrayBValueProvider implements SerializationBValueProvider<BValueArray> {

    public static final String TYPE = "TYPE";

    @Override
    public String typeName() {
        return getType().getSimpleName();
    }

    @Override
    public Class<?> getType() {
        return BValueArray.class;
    }

    @Override
    public BPacket toBValue(BValueArray array, BValueSerializer serializer) {

        long size = array.size();
        BValueArray target = new BValueArray(new BArrayType(BTypes.typeAny));
        BValue elemType = serializer.toBValue(array.getType(), null);

        if (array.getType().getTag() == TypeTags.ARRAY_TAG) {
            BType elementType = ((BArrayType) array.getType()).getElementType();
            if (elementType == BTypes.typeInt) {
                for (int i = 0; i < size; i++) {
                    BValue value = serializer.toBValue(array.getInt(i), Integer.class);
                    target.add(i, (BRefType) value);
                }
            } else if (elementType == BTypes.typeBoolean) {
                for (int i = 0; i < size; i++) {
                    BValue value = serializer.toBValue(array.getBoolean(i), Boolean.class);
                    target.add(i, (BRefType) value);
                }
            } else if (elementType == BTypes.typeByte) {
                for (int i = 0; i < size; i++) {
                    BValue value = serializer.toBValue(array.getByte(i), Byte.class);
                    target.add(i, (BRefType) value);
                }
            } else if (elementType == BTypes.typeFloat) {
                for (int i = 0; i < size; i++) {
                    BValue value = serializer.toBValue(array.getFloat(i), Double.class);
                    target.add(i, (BRefType) value);
                }
            } else if (elementType == BTypes.typeString) {
                for (int i = 0; i < size; i++) {
                    BValue value = serializer.toBValue(array.getString(i), String.class);
                    target.add(i, (BRefType) value);
                }
            } else {
                serializeRefObjArray(array, serializer, size, target);
            }
        } else if (array.getType().getTag() == TypeTags.TUPLE_TAG) {
            serializeRefObjArray(array, serializer, size, target);
        }

        return BPacket.from(typeName(), target).put(TYPE, elemType);
    }

    private void serializeRefObjArray(BValueArray array, BValueSerializer serializer, long size, BValueArray target) {
        for (int i = 0; i < size; i++) {
            BValue value = serializer.toBValue(array.getRefValue(i), null);
            target.add(i, (BRefType) value);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public BValueArray toObject(BPacket packet, BValueDeserializer bValueDeserializer) {
        BValueArray array = (BValueArray) packet.getValue();
        BType type = (BType) bValueDeserializer.deserialize(packet.get(TYPE), BType.class);
        //BType type = BTypes.getTypeFromName(elemTypeName.stringValue());
        int size = (int) array.size();
        BValueArray target = null;
        if (type.getTag() == TypeTags.INT_TAG) {
            long[] arr = new long[size];
            for (int i = 0; i < size; i++) {
                arr[i] = array.getInt(i);
            }
            target = new BValueArray(arr);
        } else if (type.getTag() == TypeTags.BOOLEAN_TAG) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = array.getBoolean(i);
            }
            target = new BValueArray(arr);
        } else if (type.getTag() == TypeTags.BYTE_TAG) {
            byte[] arr = new byte[size];
            for (int i = 0; i < size; i++) {
                arr[i] = array.getByte(i);
            }
            target = new BValueArray(arr);
        } else if (type.getTag() == TypeTags.FLOAT_TAG) {
            double[] arr = new double[size];
            for (int i = 0; i < size; i++) {
                arr[i] = array.getFloat(i);
            }
            target = new BValueArray(arr);
        } else if (type.getTag() == TypeTags.STRING_TAG) {
            String[] arr = new String[size];
            for (int i = 0; i < size; i++) {
                arr[i] = array.getString(i);
            }
            target = new BValueArray(arr);
        } else {
            target = new BValueArray(type);
            for (int i = 0; i < array.size(); i++) {
                target.add(i, (BRefType<?>) bValueDeserializer.deserialize(array.getRefValue(i), null));
            }
        }
        return target;
    }
}
