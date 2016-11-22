package com.dataart.fastforward.entity;

/**
 * Created by logariett on 22.11.2016.
 */

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Marks",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "idea_id"}) })
public class Mark {
    //TODO
}
