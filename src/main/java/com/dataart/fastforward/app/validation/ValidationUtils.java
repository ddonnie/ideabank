package com.dataart.fastforward.app.validation;

import com.dataart.fastforward.app.exceptions.UnauthorisedIdeaUpdateException;
import com.dataart.fastforward.app.model.Idea;
import com.dataart.fastforward.app.model.User;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import com.dataart.fastforward.app.services.utils.ReflectionUtils;
import org.apache.log4j.Logger;

/**
 * Created by Orlov on 28.11.2016.
 */
public class ValidationUtils {
    final static Logger LOGGER = Logger.getLogger(ValidationUtils.class);

    private ValidationUtils() {
        throw new NotImplementedException("Utility classes cannot be instantiated");
    }

    public static void assertNotBlank(String fieldValue, String message) {
        if (StringUtils.isBlank(fieldValue)) {
            throw new IllegalArgumentException(message + " cannot be empty");
        }
    }

    public static void assertExistsNotBlank(String[] fieldValues) {
        for (String fieldValue : fieldValues)
            if (StringUtils.isNotBlank(fieldValue))
                return;
        throw new IllegalArgumentException("At least one field should be filled");
    }

    public static void assertExistsNotBlank(Object o) {
        try{
            String[] fieldValues = ReflectionUtils.getAllNonCollectionFieldsValuesAsStrings(o);
            assertExistsNotBlank(fieldValues);
        }
        catch (IllegalAccessException ex) {
            LOGGER.error("YOU HAVE FAILED ME SON", ex);
        }
    }

    public static void assertAuthor(Idea idea, User user) {
        if (!idea.getAuthor().equals(user) && !"ADMIN".equals(user.getRole().getRoleName()) ) {
            RuntimeException ex = new UnauthorisedIdeaUpdateException("Idea {id:"
                    + idea.getIdeaId() + "} doesn't belong to User {id:"
                    + user.getUserId() +",username:" + user.getUsername()
                    + "}. Action denied");
            LOGGER.warn("", ex);
            throw ex;
        }
    }
}
