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
package org.ballerinalang.langserver.completions.providers.subproviders.parsercontext;

import org.ballerinalang.langserver.compiler.LSContext;
import org.ballerinalang.langserver.completions.spi.LSCompletionProvider;
import org.ballerinalang.langserver.completions.util.ItemResolverConstants;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;

import java.util.ArrayList;
import java.util.List;

/**
 * annotation body context resolver for the completion items.
 */
public class ParserRuleAttachmentPointCompletionProvider extends LSCompletionProvider {
    @Override
    public List<CompletionItem> getCompletions(LSContext completionContext) {
        ArrayList<CompletionItem> completionItems = new ArrayList<>();
        completionItems.add(populateCompletionItem(ItemResolverConstants.ANNOTATION, ItemResolverConstants.KEYWORD_TYPE,
                ItemResolverConstants.ANNOTATION));
        completionItems.add(populateCompletionItem(ItemResolverConstants.FUNCTION, ItemResolverConstants.KEYWORD_TYPE, 
                ItemResolverConstants.FUNCTION));
        completionItems.add(populateCompletionItem(ItemResolverConstants.RESOURCE, ItemResolverConstants.KEYWORD_TYPE,
                ItemResolverConstants.RESOURCE));
        completionItems.add(populateCompletionItem(ItemResolverConstants.SERVICE, ItemResolverConstants.KEYWORD_TYPE,
                ItemResolverConstants.SERVICE));

        return completionItems;
    }

    /**
     * Populate a completion item with the given data and return it.
     *
     * @param insertText                insert text
     * @param type                      type of the completion item
     * @param label                     completion item label
     * @return {@link CompletionItem}   Populated Completion Item
     */
    private CompletionItem populateCompletionItem(String insertText, String type, String label) {
        CompletionItem completionItem = new CompletionItem();
        completionItem.setInsertText(insertText);
        completionItem.setDetail(type);
        completionItem.setLabel(label);
        completionItem.setKind(CompletionItemKind.Keyword);
        return completionItem;
    }
}
