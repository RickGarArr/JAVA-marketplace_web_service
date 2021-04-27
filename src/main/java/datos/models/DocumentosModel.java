package datos.models;

import java.util.Objects;

public class DocumentosModel {
    private int id_documentos;
    private String doc_ide;
    private String doc_rfc;
    private String doc_dom;

    public DocumentosModel() {
    }

    public DocumentosModel(String doc_ide, String doc_rfc, String doc_dom) {
        this.doc_ide = doc_ide;
        this.doc_rfc = doc_rfc;
        this.doc_dom = doc_dom;
    }

    public DocumentosModel(int id_documentos, String doc_ide, String doc_rfc, String doc_dom) {
        this.id_documentos = id_documentos;
        this.doc_ide = doc_ide;
        this.doc_rfc = doc_rfc;
        this.doc_dom = doc_dom;
    }

    public int getId_documentos() {
        return id_documentos;
    }

    public void setId_documentos(int id_documentos) {
        this.id_documentos = id_documentos;
    }

    public String getDoc_ide() {
        return doc_ide;
    }

    public void setDoc_ide(String doc_ide) {
        this.doc_ide = doc_ide;
    }

    public String getDoc_rfc() {
        return doc_rfc;
    }

    public void setDoc_rfc(String doc_rfc) {
        this.doc_rfc = doc_rfc;
    }

    public String getDoc_dom() {
        return doc_dom;
    }

    public void setDoc_dom(String doc_dom) {
        this.doc_dom = doc_dom;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.id_documentos;
        hash = 59 * hash + Objects.hashCode(this.doc_ide);
        hash = 59 * hash + Objects.hashCode(this.doc_rfc);
        hash = 59 * hash + Objects.hashCode(this.doc_dom);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentosModel other = (DocumentosModel) obj;
        if (this.id_documentos != other.id_documentos) {
            return false;
        }
        if (!Objects.equals(this.doc_ide, other.doc_ide)) {
            return false;
        }
        if (!Objects.equals(this.doc_rfc, other.doc_rfc)) {
            return false;
        }
        if (!Objects.equals(this.doc_dom, other.doc_dom)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DocumentosModel{" + "id_documentos=" + id_documentos + ", doc_ide=" + doc_ide + ", doc_rfc=" + doc_rfc + ", doc_dom=" + doc_dom + '}';
    }
}
