/*
 * Copyright (c) 2011 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *	Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *	Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
 *
 *  WebSite:
 *  http://code.google.com/p/pony-sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ponysdk.ui.server.form;

import java.util.ArrayList;
import java.util.List;

import com.ponysdk.ui.server.basic.PWidget;
import com.ponysdk.ui.server.form.formfield.FormField;
import com.ponysdk.ui.server.form.validator.ValidationResult;

/**
 * A list of {@link FormField} validated or reset altogether
 */
public class Form {

    protected final List<FormField<?, ? extends PWidget>> formFields = new ArrayList<>();

    public void addFormField(final FormField<?, ? extends PWidget> formField) {
        formFields.add(formField);
    }

    public void removeFormField(final FormField<?, ? extends PWidget> formField) {
        formFields.remove(formField);
    }

    public boolean isValid() {
        boolean valid = true;
        for (final FormField<?, ? extends PWidget> formField : formFields) {
            final ValidationResult result = formField.isValid();
            if (!result.isValid()) {
                valid = false;
            }
        }
        return valid;
    }

    public void reset() {
        for (final FormField<?, ? extends PWidget> formField : formFields) {
            formField.reset();
        }
    }

    public List<FormField<?, ? extends PWidget>> getFormFields() {
        return formFields;
    }

}