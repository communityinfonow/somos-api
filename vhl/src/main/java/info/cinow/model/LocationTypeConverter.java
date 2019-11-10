package info.cinow.model;

import java.beans.PropertyEditorSupport;

/**
 * LocationTypeConverter
 */
public class LocationTypeConverter extends PropertyEditorSupport {

    public void setAsText(final String text) throws IllegalArgumentException {
        setValue(LocationType.fromValue(text));
    }
}