package com.dataart.fastforward.app.services.utils;

import com.dataart.fastforward.app.dto.CommentDTO;
import com.dataart.fastforward.app.dto.IdeaDTO;
import com.dataart.fastforward.app.dto.NewUserDTO;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

import static org.apache.commons.lang3.StringUtils.normalizeSpace;

/**
 * @author logariett on 27.12.2016.
 */
public class Utills {

    public static void normalizeAllStrings(Object obj) {
        Class cls = obj.getClass();

        if (IdeaDTO.class.equals(cls)) {
            IdeaDTO ideaDTO = ((IdeaDTO) obj);

            ideaDTO.setIdeaName(normalizeSpace(ideaDTO.getIdeaName()));
            ideaDTO.setIdeaText(normalizeSpace(ideaDTO.getIdeaText()));

            for (String tag : ideaDTO.getTags())
                normalizeSpace(tag);

        } else if (CommentDTO.class.equals(cls)) {
            CommentDTO commentDTO = (CommentDTO) obj;

            commentDTO.setCommentText(normalizeSpace(commentDTO.getCommentText()));

        } else if (NewUserDTO.class.equals(cls)) {
            NewUserDTO userDTO = (NewUserDTO) obj;

            userDTO.setFirstName(normalizeSpace(userDTO.getFirstName()));
            userDTO.setLastName(normalizeSpace(userDTO.getLastName()));
        }
    }
}
