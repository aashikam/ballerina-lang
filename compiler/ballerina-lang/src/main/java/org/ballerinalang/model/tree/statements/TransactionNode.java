/*
*  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/
package org.ballerinalang.model.tree.statements;

import org.ballerinalang.model.tree.expressions.ExpressionNode;
import org.wso2.ballerinalang.compiler.tree.statements.BLangBlockStmt;

/**
 * {@code TransactionNode} represents the transaction statement in Ballerina.
 *
 * @since 0.94
 */
public interface TransactionNode extends StatementNode {
    BlockNode getTransactionBody();

    BlockNode getOnRetryBody();

    ExpressionNode getRetryCount();

    BLangBlockStmt getAbortedBody();

    BLangBlockStmt getCommittedBody();

    void setTransactionBody(BlockNode body);

    void setOnRetryBody(BlockNode body);

    void setRetryCount(ExpressionNode retryCount);

    void setCommittedBody(BlockNode committedBlock);

    void setAbortedBody(BlockNode abortedBlock);
}
