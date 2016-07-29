package services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import models.Pet;

public class MongoDbPetService {

	private static String PET_COLLECTION_NAME = "Pet";

	private static MongoDbPetService instance = new MongoDbPetService();

	public static MongoDbPetService getInstance() {
		if (instance == null) {
			synchronized (MongoDbPetService.class) {
				if (instance == null) {
					instance = new MongoDbPetService();
				}
			}
		}
		return instance;
	}

	private MongoDbPetService() {

	}

	public void add(Pet pet) {
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		try {

			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDatabase("play-java-pets");

			// TODO authentication
			MongoCollection<Document> coll = db.getCollection(PET_COLLECTION_NAME);

			Document doc = new Document("_id", pet.getId()).append("name", pet.getName()).append("age", pet.getAge())
					.append("sex", pet.getSex());

			coll.insertOne(doc);
			coll = db.getCollection(PET_COLLECTION_NAME);
			MongoCursor<Document> cursor = coll.find().iterator();
			int i = 1;

			while (cursor.hasNext()) {
				System.out.println("Inserted Document: " + i);
				System.out.println(cursor.next());
				i++;
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			try {
				mongoClient.close();
			} catch (Exception e1) {
			}
		}

	}

	public void update(Pet pet) {
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		try {

			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDatabase("play-java-pets");

			// TODO authentication
			MongoCollection<Document> coll = db.getCollection(PET_COLLECTION_NAME);

			Document query = new Document("_id", pet.getId());

			Document doc = new Document("_id", pet.getId()).append("name", pet.getName()).append("age", pet.getAge())
					.append("sex", pet.getSex());

			coll.replaceOne(query, doc);

			MongoCursor<Document> cursor = coll.find().iterator();
			int i = 1;

			while (cursor.hasNext()) {
				System.out.println("Inserted Document: " + i);
				System.out.println(cursor.next());
				i++;
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			try {
				mongoClient.close();
			} catch (Exception e1) {
			}
		}

	}

	public void delete(Pet pet) {
		System.out.println("--------------- Delete --------------------");
		System.out.println("Pet id" + pet.getId());
		
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		try {

			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDatabase("play-java-pets");

			MongoCollection<Document> coll = db.getCollection(PET_COLLECTION_NAME);

			Document query = new Document("_id", pet.getId());

			coll.deleteMany(query);

			MongoCursor<Document> cursor = coll.find().iterator();
			int i = 1;

			while (cursor.hasNext()) {
				System.out.println("Inserted Document: " + i);
				System.out.println(cursor.next());
				i++;
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			try {
				mongoClient.close();
			} catch (Exception e1) {
			}
		}

	}

	public void deleteAll() {
		MongoClient mongoClient = null;
		MongoDatabase db = null;
		try {

			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDatabase("play-java-pets");

			MongoCollection<Document> coll = db.getCollection(PET_COLLECTION_NAME);

			Document query = new Document();

			coll.deleteMany(query);

			MongoCursor<Document> cursor = coll.find().iterator();
			int i = 1;

			while (cursor.hasNext()) {
				System.out.println("Inserted Document: " + i);
				System.out.println(cursor.next());
				i++;
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			try {
				mongoClient.close();
			} catch (Exception e1) {
			}
		}

	}

	public Pet fetch(Pet pet) {
		Pet result = null;

		MongoClient mongoClient = null;
		MongoDatabase db = null;
		try {

			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDatabase("play-java-pets");

			MongoCollection<Document> coll = db.getCollection(PET_COLLECTION_NAME);

			Document query = new Document("_id", pet.getId());

			MongoCursor<Document> cursor = coll.find(query).iterator();
			if (cursor.hasNext()) {
				Document doc = cursor.next();
				result = new Pet();
				result.setId(doc.getString("_id"));
				result.setName(doc.getString("name"));
				result.setAge(doc.getInteger("age"));
				result.setSex(doc.getString("sex"));
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			try {
				mongoClient.close();
			} catch (Exception e1) {
			}
		}
		return result;
	}

	public List<Pet> fetchAll() {
		List<Pet> result = new ArrayList<Pet>();

		MongoClient mongoClient = null;
		MongoDatabase db = null;
		try {

			mongoClient = new MongoClient("localhost", 27017);
			db = mongoClient.getDatabase("play-java-pets");

			MongoCollection<Document> coll = db.getCollection(PET_COLLECTION_NAME);

			MongoCursor<Document> cursor = coll.find().iterator();
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				Pet p = new Pet();
				p.setId(doc.getString("_id"));
				p.setName(doc.getString("name"));
				p.setAge(doc.getInteger("age"));
				p.setSex(doc.getString("sex"));
				result.add(p);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			try {
				mongoClient.close();
			} catch (Exception e1) {
			}
		}
		return result;
	}

}
