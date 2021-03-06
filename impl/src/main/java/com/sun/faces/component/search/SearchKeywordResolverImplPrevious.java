/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.component.search;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.search.UntargetableComponent;
import javax.faces.component.search.SearchExpressionContext;
import javax.faces.component.search.SearchKeywordContext;
import javax.faces.component.search.SearchKeywordResolver;

public class SearchKeywordResolverImplPrevious extends SearchKeywordResolver {

    @Override
    public void resolve(SearchKeywordContext searchKeywordContext, UIComponent current, String keyword) {
        UIComponent parent = current.getParent();

        if (parent.getChildCount() > 1) {
            List<UIComponent> children = parent.getChildren();
            int index = children.indexOf(current);

            if (index > 0) {
                int nextIndex = -1;
                do {
                    index--;
                    if (!(children.get(index) instanceof UntargetableComponent)) {
                        nextIndex = index;
                    }
                } while (nextIndex == -1 && index > 0);

                if (nextIndex != -1) {
                    searchKeywordContext.invokeContextCallback(children.get(nextIndex));
                }
            }
        }

        searchKeywordContext.setKeywordResolved(true);
    }

    @Override
    public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String keyword) {
        return "previous".equals(keyword);
    }
    
}
