package org.verapdf.model.factory.operator;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.verapdf.model.impl.pb.pd.colors.PBoxPDColorSpace;
import org.verapdf.model.impl.pb.pd.font.PBoxPDFont;
import org.verapdf.model.impl.pb.pd.images.PBoxPDXObject;

import java.io.IOException;

/**
 * Implementation of graphic state for content stream.
 * Contains follows settings: fill and stroke color spaces,
 * pattern, rendering mode and font
 *
 * @author Timur Kamalov
 */
public class GraphicState implements Cloneable {

    private static final Logger LOGGER = Logger.getLogger(GraphicState.class);

    private PDColorSpace fillColorSpace = PDDeviceGray.INSTANCE;
    private PDColorSpace strokeColorSpace = PDDeviceGray.INSTANCE;
    private PDAbstractPattern fillPattern = null;
	private PDAbstractPattern strokePattern = null;
    private RenderingMode renderingMode = RenderingMode.FILL;
    private COSName fontName;

	// fields for transparency checks
	private COSBase sMask = null;
	private float ca = 1;
	private float ca_ns = 1;
	private COSBase bm = null;

	// fields for transparency checks. This is veraPDF implementation of XObject
	private PBoxPDXObject veraXObject = null;
	private PBoxPDColorSpace veraFillColorSpace = null;
	private PBoxPDColorSpace veraStrokeColorSpace = null;
	private PBoxPDFont veraFont = null;

	/**
	 * @return fill color space of current state
	 */
    public PDColorSpace getFillColorSpace() {
        return fillColorSpace;
    }

	/**
	 * @param fillColorSpace set fill color space to current state
	 */
    public void setFillColorSpace(PDColorSpace fillColorSpace) {
        this.fillColorSpace = fillColorSpace;
    }

	/**
	 * @return stroke color space of current state
	 */
    public PDColorSpace getStrokeColorSpace() {
        return strokeColorSpace;
    }

	/**
	 * @param strokeColorSpace set stroke color space to current state
	 */
    public void setStrokeColorSpace(PDColorSpace strokeColorSpace) {
        this.strokeColorSpace = strokeColorSpace;
    }

	public PDAbstractPattern getFillPattern() {
		return fillPattern;
	}

	public void setFillPattern(PDAbstractPattern fillPattern) {
		this.fillPattern = fillPattern;
	}

	public PDAbstractPattern getStrokePattern() {
		return strokePattern;
	}

	public void setStrokePattern(PDAbstractPattern strokePattern) {
		this.strokePattern = strokePattern;
	}

	public PBoxPDColorSpace getVeraFillColorSpace() {
		return veraFillColorSpace;
	}

	public void setVeraFillColorSpace(PBoxPDColorSpace veraFillColorSpace) {
		this.veraFillColorSpace = veraFillColorSpace;
	}

	public PBoxPDColorSpace getVeraStrokeColorSpace() {
		return veraStrokeColorSpace;
	}

	public void setVeraStrokeColorSpace(PBoxPDColorSpace veraStrokeColorSpace) {
		this.veraStrokeColorSpace = veraStrokeColorSpace;
	}

	public PBoxPDFont getVeraFont() {
		return veraFont;
	}

	public void setVeraFont(PBoxPDFont veraFont) {
		this.veraFont = veraFont;
	}

	/**
	 * @return rendering mode of current state
	 */
    public RenderingMode getRenderingMode() {
        return renderingMode;
    }

	/**
	 * @param renderingMode set renderint mode to color space
	 */
    public void setRenderingMode(RenderingMode renderingMode) {
        this.renderingMode = renderingMode;
    }

    /**
     * @return name of the current font
     */
    public COSName getFontName() {
        return fontName;
    }

    /**
     * @param fontName set name of the current font
     */
    public void setFontName(COSName fontName) {
        this.fontName = fontName;
    }

	public COSBase getSMask() {
		return sMask;
	}

	public void setSMask(COSBase sMask) {
		this.sMask = sMask;
	}

	public float getCa() {
		return ca;
	}

	public void setCa(float ca) {
		this.ca = ca;
	}

	public float getCa_ns() {
		return ca_ns;
	}

	public void setCa_ns(float ca_ns) {
		this.ca_ns = ca_ns;
	}

	public COSBase getBm() {
		return bm;
	}

	public void setBm(COSBase bm) {
		this.bm = bm;
	}

	public PBoxPDXObject getVeraXObject() {
		return veraXObject;
	}

	public void setVeraXObject(PBoxPDXObject veraXObject) {
		this.veraXObject = veraXObject;
	}

	/**
     * This method will copy properties from passed graphic state to current
     * object
     * 
     * @param graphicState
     *            graphic state to copy properties from
     */
    public void copyProperties(GraphicState graphicState) {
        this.fillColorSpace = graphicState.getFillColorSpace();
        this.strokeColorSpace = graphicState.getStrokeColorSpace();
        this.fillPattern = graphicState.getFillPattern();
		this.strokePattern = graphicState.getStrokePattern();
        this.renderingMode = graphicState.getRenderingMode();
        this.fontName = graphicState.getFontName();
		this.sMask = graphicState.getSMask();
		this.ca_ns = graphicState.getCa_ns();
		this.ca = graphicState.getCa();
		this.bm = graphicState.getBm();
		this.veraXObject = graphicState.getVeraXObject();
		this.veraFillColorSpace = graphicState.getVeraFillColorSpace();
		this.veraStrokeColorSpace = graphicState.getVeraStrokeColorSpace();
		this.veraFont = graphicState.getVeraFont();
    }

	/**
	 * Set font to current state from extended graphic state
	 *
	 * @param extGState extended graphic state
	 */
    public void copyPropertiesFromExtGState(PDExtendedGraphicsState extGState) {
        if (extGState != null) {
            try {
                if (extGState.getFontSetting() != null) {
                    this.fontName = COSName.getPDFName(extGState.getFontSetting().getFont().getName());
                }
				COSDictionary cosObject = extGState.getCOSObject();
				COSBase smask = cosObject.getDictionaryObject(COSName.SMASK);
				if (smask != null) {
					this.sMask = smask;
				}
				COSBase bm = cosObject.getDictionaryObject(COSName.BM);
				if (bm != null) {
					this.bm = bm;
				}
				COSBase ca_ns_base = cosObject.getDictionaryObject(COSName.CA_NS);
				if (ca_ns_base instanceof COSNumber) {
					this.ca_ns = ((COSNumber) ca_ns_base).floatValue();
				}
				COSBase ca_base = cosObject.getDictionaryObject(COSName.CA);
				if (ca_base instanceof COSNumber) {
					this.ca = ((COSNumber) ca_base).floatValue();
				}
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

	/**
	 * Create copy of current graphic state.
	 *
	 * @return copy of current graphic state
	 * @throws CloneNotSupportedException
	 */
    @Override
    public GraphicState clone() throws CloneNotSupportedException {
        GraphicState graphicState = (GraphicState) super.clone();
        graphicState.fillColorSpace = this.fillColorSpace;
        graphicState.strokeColorSpace = this.strokeColorSpace;
        graphicState.fillPattern = this.fillPattern;
		graphicState.strokePattern = this.strokePattern;
        graphicState.renderingMode = this.renderingMode;
        graphicState.fontName = this.fontName;
		graphicState.sMask = this.sMask;
		graphicState.ca_ns = this.ca_ns;
		graphicState.ca = this.ca;
		graphicState.bm = this.bm;
		graphicState.veraXObject = this.veraXObject;
		graphicState.veraFillColorSpace = this.veraFillColorSpace;
		graphicState.veraStrokeColorSpace = this.veraStrokeColorSpace;
		graphicState.veraFont = this.veraFont;
        return graphicState;
    }

}
