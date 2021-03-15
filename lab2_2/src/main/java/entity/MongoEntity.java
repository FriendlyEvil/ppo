package entity;

import org.bson.Document;

/**
 * @author friendlyevil
 */
public interface MongoEntity {
    Document toDocument();
}
