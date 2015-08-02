package org.verapdf.model.impl.pb.pd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.verapdf.model.baselayer.Object;
import org.verapdf.model.factory.colors.ColorSpaceFactory;
import org.verapdf.model.pdlayer.PDColorSpace;
import org.verapdf.model.pdlayer.PDGroup;

/**
 * @author Evgeniy Muravitskiy
 */
public class PBoxPDGroup extends PBoxPDObject implements PDGroup {

    private static final Logger LOGGER = Logger.getLogger(PBoxPDGroup.class);

    public static final String COLOR_SPACE = "colorSpace";
    public static final int MAX_COLOR_SPACES = 1;
    public static final String GROUP_TYPE = "PDGroup";

    public PBoxPDGroup(
            org.apache.pdfbox.pdmodel.graphics.form.PDGroup simplePDObject) {
        super(simplePDObject);
        setType(GROUP_TYPE);
    }

    @Override
    public String getS() {
        return ((org.apache.pdfbox.pdmodel.graphics.form.PDGroup) simplePDObject)
                .getSubType().getName();
    }

    @Override
    public List<? extends Object> getLinkedObjects(String link) {
        if (COLOR_SPACE.equals(link)) {
            return this.getColorSpace();
        }
        return super.getLinkedObjects(link);
    }

    private List<PDColorSpace> getColorSpace() {
        List<PDColorSpace> colorSpaces = new ArrayList<>(MAX_COLOR_SPACES);
        try {
            org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace pbColorSpace = ((org.apache.pdfbox.pdmodel.graphics.form.PDGroup) simplePDObject)
                    .getColorSpace();
            PDColorSpace colorSpace = ColorSpaceFactory
                    .getColorSpace(pbColorSpace);
            if (colorSpace != null) {
                colorSpaces.add(colorSpace);
            }
        } catch (IOException e) {
            LOGGER.error(
                    "Problems with color space obtaining on group. "
                            + e.getMessage(), e);
        }
        return colorSpaces;
    }
}
