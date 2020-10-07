/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/
package org.apache.plc4x.java.can.field;

import org.apache.plc4x.java.api.exceptions.PlcInvalidFieldException;
import org.apache.plc4x.java.canopen.readwrite.types.CANOpenDataType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CANOpenPDOField extends CANOpenField {

    public static final Pattern ADDRESS_PATTERN = Pattern.compile("PDO:" + CANOpenField.NODE_PATTERN + ":(?<canDataType>\\w+)(\\[(?<numberOfElements>\\d)])?");
    private final CANOpenDataType canOpenDataType;

    public CANOpenPDOField(int node, CANOpenDataType canOpenDataType) {
        super(node);
        this.canOpenDataType = canOpenDataType;
    }

    public CANOpenDataType getCanOpenDataType() {
        return canOpenDataType;
    }

    public static boolean matches(String addressString) {
        return ADDRESS_PATTERN.matcher(addressString).matches();
    }

    public static Matcher getMatcher(String addressString) throws PlcInvalidFieldException {
        Matcher matcher = ADDRESS_PATTERN.matcher(addressString);
        if (matcher.matches()) {
            return matcher;
        }

        throw new PlcInvalidFieldException(addressString, ADDRESS_PATTERN);
    }

    public static CANOpenPDOField of(String addressString) {
        Matcher matcher = getMatcher(addressString);
        int nodeId = Integer.parseInt(matcher.group("nodeId"));

        String canDataTypeString = matcher.group("canDataType");
        CANOpenDataType canOpenDataType = CANOpenDataType.valueOf(canDataTypeString);

        return new CANOpenPDOField(nodeId, canOpenDataType);
    }

}
