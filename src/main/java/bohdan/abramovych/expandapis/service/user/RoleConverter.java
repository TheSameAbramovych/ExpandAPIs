package bohdan.abramovych.expandapis.service.user;

import bohdan.abramovych.expandapis.model.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        return role.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        return UserRole.getRole(dbData);
    }
}
