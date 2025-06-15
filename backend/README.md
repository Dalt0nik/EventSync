# EventSync Backend

## Prerequisites

- Java 17 JDK
- Intellij IDEA

## Running with IntelliJ IDEA

### 1. Open the project in IntelliJ IDEA:

File → Open → Select the backend folder

### 2. Set up environment variables

1. Generate free api key on [HuggingFace](https://huggingface.co/).
2. Set `HUGGINGFACE_API_KEY` key in the run configuration in Intellij Idea:

    Go to Run → Edit Configurations.

    Select your Spring Boot run configuration.

    Click Modify Options → Environment Variables.

    Add: HUGGINGFACE_API_KEY=yourApiKey;


### 3. Run the application
Use Run → Run 'BackendApplication'


## Documentation

Endpoints can be seen in Swagger using the following link:

```
http://localhost:8080/api/swagger-ui/index.html
```

## Deployment