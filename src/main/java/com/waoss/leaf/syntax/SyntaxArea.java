/*
 *  Copyright (c) Waoss
 *
 *  Mail me at rahul29112002@gmail.com for any queries :)
 *
 *  This is free software licensed under the GNU General Public License.This license allows one to modify it on their will and also embed it or distribute it along with their own software.
 *  It is distributed in the hope that it shall be useful to whomsoever receives it,but does not provide ANY warranty or liability,not even the gurantee that the software will work in your certain usage.
 *  You receive a copy of the GNU General Public License version 3.0 when you download this software.See LICENSE.MD for more details.
 */

package com.waoss.leaf.syntax;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SyntaxArea extends AbstractSyntaxArea {

    public SyntaxArea() {
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        this.richChanges().filter(change -> !change.getInserted().equals(change.getRemoved()))
                .subscribe(change -> {
                    this.setStyleSpans(0, computeHighlighting(this.getText()));
                });
    }

    @Override
    public StyleSpans<Collection<String>> computeHighlighting(String text) {
        StyleSpansBuilder<Collection<String>> styleSpansBuilder = new StyleSpansBuilder<>();
        List<String> list = Arrays.asList("");
        styleSpansBuilder.add(Collections.emptyList(), 0);
        return styleSpansBuilder.create();
    }

    @Override
    public String getStylesheet() {
        return null;
    }
}
