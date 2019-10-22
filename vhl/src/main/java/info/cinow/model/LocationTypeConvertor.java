package info.cinow.model;

import java.beans.PropertyEditorSupport;

/**
 * LocationTypeConvertor
 */
public class LocationTypeConvertor extends PropertyEditorSupport {

    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(LocationType.fromValue(text));
    }
}