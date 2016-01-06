package org.verapdf.model.impl.axl;

import com.adobe.xmp.impl.VeraPDFXMPNode;
import com.adobe.xmp.impl.XMPSchemaRegistryImpl;
import org.verapdf.model.tools.xmp.ValidatorsContainer;
import org.verapdf.model.tools.xmp.validators.SimpleTypeValidator;
import org.verapdf.model.xmplayer.ExtensionSchemaProperty;

/**
 * @author Maksim Bezrukov
 */
public class AXLExtensionSchemaProperty extends AXLExtensionSchemaObject implements ExtensionSchemaProperty {

    public static final String EXTENSION_SCHEMA_PROPERTY = "ExtensionSchemaProperty";

    public AXLExtensionSchemaProperty(VeraPDFXMPNode xmpNode, ValidatorsContainer containerForPDFA_1, ValidatorsContainer containerForPDFA_2_3) {
        super(EXTENSION_SCHEMA_PROPERTY, xmpNode, containerForPDFA_1, containerForPDFA_2_3);
    }

    @Override
    public Boolean getisValueTypeCorrect() {
        boolean isValid = true;
        boolean isCategoryPresent = false;
        boolean isDescriptionPresent = false;
        boolean isNamePresent = false;
        boolean isValueTypePresent = false;
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI())) {
                switch (child.getName()) {
                    case "category":
                        if (isCategoryPresent) {
                            isValid = false;
                        } else {
                            isCategoryPresent = true;
                        }
                        break;
                    case "description":
                        if (isDescriptionPresent) {
                            isValid = false;
                        } else {
                            isDescriptionPresent = true;
                        }
                        break;
                    case "name":
                        if (isNamePresent) {
                            isValid = false;
                        } else {
                            isNamePresent = true;
                        }
                        break;
                    case "valueType":
                        if (isValueTypePresent) {
                            isValid = false;
                        } else {
                            isValueTypePresent = true;
                        }
                        break;
                    default:
                        isValid = false;
                }
            } else {
                isValid = false;
            }
        }
        return Boolean.valueOf(isValid);
    }

    @Override
    public Boolean getisCategoryValid() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "category".equals(child.getName())) {
                String childValue = child.getValue();
                return "internal".equals(childValue) || "external".equals(childValue);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean getisDescriptionValid() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "description".equals(child.getName())) {
                return Boolean.valueOf(SimpleTypeValidator.fromValue(SimpleTypeValidator.SimpleTypeEnum.TEXT).isCorresponding(child));
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean getisNameValid() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "name".equals(child.getName())) {
                return Boolean.valueOf(SimpleTypeValidator.fromValue(SimpleTypeValidator.SimpleTypeEnum.TEXT).isCorresponding(child));
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean getisValueTypeValidForPDFA_1() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "valueType".equals(child.getName())) {
                return containerForPDFA_1.isKnownType(child.getValue());
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean getisValueTypeValidForPDFA_2_3() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "valueType".equals(child.getName())) {
                return containerForPDFA_2_3.isKnownType(child.getValue());
            }
        }
        return Boolean.TRUE;
    }


    @Override
    public String getcategoryPrefix() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "category".equals(child.getName())) {
                return child.getPrefix();
            }
        }
        return null;
    }

    @Override
    public String getdescriptionPrefix() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "description".equals(child.getName())) {
                return child.getPrefix();
            }
        }
        return null;
    }

    @Override
    public String getnamePrefix() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "name".equals(child.getName())) {
                return child.getPrefix();
            }
        }
        return null;
    }

    @Override
    public String getvalueTypePrefix() {
        for (VeraPDFXMPNode child : this.xmpNode.getChildren()) {
            if (XMPSchemaRegistryImpl.NS_PDFA_PROPERTY.equals(child.getNamespaceURI()) && "valueType".equals(child.getName())) {
                return child.getPrefix();
            }
        }
        return null;
    }
}
