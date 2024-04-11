# Manatee API

The following API is made for educational purposes only and does not provide any meaningful functionalities.

## Getting started

This project requires Java 21 to be installed.
For developers, Amazon Coretta or Eclipse Termium are recommended JDKs.

For development purposes only, the relational H2 database is initialized in the local runtime.
On the shutdown, the database is torn down. There is no other option to set a persistent database.

### For Linux users (bash)

```bash
./gradlew build # Generates OpenAPI models, builds the application and runs tests.
./gradlew bootRun # Starts the application on a local network. 
```

### For Windows users

```bash
gradlew build # Generates OpenAPI models, builds the application and runs tests.
gradlew bootRun # Starts the application on a local network. 
```


# Summary
| Question                                 | Answer                                                                         |
|------------------------------------------|--------------------------------------------------------------------------------|
| Time  spent (h)                          | ~12h (plus minus, like, 15 minutes)                                            |
| Hardest task, (with reasoning)           | 1&2(Accidentally caused infinite loop with dtos and took a long time to debug) |
| Uncompleted tasks, if any                | -                                                                              |
| Additional dependencies (with reasoning) | -                                                                              | 


In summary, describe your overall experience with the topic, what you learned,
and any technical challenges you encountered. Your answer should be
between 50-100 words.

SUMMARY:
I have some previous experience with spring boot, so creating database entities, services, models etc. didn't take that
long. Although I haven't implemented a manyToOne/oneToMany relationship before. That did cause some headache because
for one I forgot to initialize the list of interviews in the application service. Secondly I now know that
@Entity does not play well with lombok @Data which overwrites ToString and EqualsAndHashCode. Finally, I had a problem
with an infinite loop when getting applications which was related to my yaml. Also, I've yet  to use openApiGenerate,
and oh boy does it speed things up when developing something new.