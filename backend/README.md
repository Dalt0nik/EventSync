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

### Prerequisites
- Railway CLI installed: `npm install -g @railway/cli`
- Railway account

### Steps

1. Login and create project:

```bash
npx railway login
npx railway init
```

2. Navigate to backend and link service:
```bash
cd backend/
npx railway link
npx railway service  # Select "backend"
```

3. Create Railway volume for H2 database:
- Go to Railway dashboard → your backend service
- Open Command Palette
- Type "Create Volume"
- Set mount path to: /data

4. Set environment variables (via Railway dashboard)
```bash
RAILWAY_RUN_UID=0
JAVA_OPTS=-Xmx512m -XX:+UseG1GC
SPRING_PROFILES_ACTIVE=prod
HUGGINGFACE_API_KEY=your_hf_key_here
```

5. Deploy:
```bash
npx railway up
npx railway domain
```