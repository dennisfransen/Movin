package dennisfransen.iths.se.movin;

public class CompanyModel {

    private String company_name, company_org_number, company_address, company_contact_number;
    private boolean company_cleaning_type, company_moving_type;

    public CompanyModel() {
    }

    public CompanyModel(String company_name, String company_org_number, boolean company_cleaning_type, boolean company_moving_type) {
        this.company_name = company_name;
        this.company_org_number = company_org_number;
        this.company_cleaning_type = company_cleaning_type;
        this.company_moving_type = company_moving_type;
    }

    public CompanyModel(boolean company_cleaning_type, boolean company_moving_type) {
        this.company_name = company_name;
        this.company_org_number = company_org_number;
        this.company_address = company_address;
        this.company_contact_number = company_contact_number;
        this.company_cleaning_type = company_cleaning_type;
        this.company_moving_type = company_moving_type;
    }

    public CompanyModel(String company_name, String company_org_number, String company_address, String company_contact_number, boolean company_cleaning_type, boolean company_moving_type) {
        this.company_name = company_name;
        this.company_org_number = company_org_number;
        this.company_address = company_address;
        this.company_contact_number = company_contact_number;
        this.company_cleaning_type = company_cleaning_type;
        this.company_moving_type = company_moving_type;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public void setCompany_org_number(String company_org_number) {
        this.company_org_number = company_org_number;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public void setCompany_contact_number(String company_contact_number) {
        this.company_contact_number = company_contact_number;
    }

    public void setCompany_cleaning_type(boolean company_cleaning_type) {
        this.company_cleaning_type = company_cleaning_type;
    }

    public void setCompany_moving_type(boolean company_moving_type) {
        this.company_moving_type = company_moving_type;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_org_number() {
        return company_org_number;
    }

    public String getCompany_address() {
        return company_address;
    }

    public String getCompany_contact_number() {
        return company_contact_number;
    }

    public boolean isCompany_cleaning_type() {
        return company_cleaning_type;
    }

    public boolean isCompany_moving_type() {
        return company_moving_type;
    }
}