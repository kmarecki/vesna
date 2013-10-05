/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vesna.core.javafx;

/**
 *
 * @author Krzysztof Marecki
 */
public class BaseController<TModel extends BaseModel> {
   
    private TModel model;

    protected TModel getModel() {
        return model;
    }

    public void setModel(TModel model) {
        this.model = model;
    }
}
