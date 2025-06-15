# EventSync Frontend

## Prerequisites
- Node.js 16.0.0 or higher (tested with v20.11)

## How to Run

```bash
cd frontend
npm install
npm run dev
```

## Deployment

### Prerequisites

- Backend service deployed and running
- Railway CLI installed

### Steps

1. Link to frontend service:
```bash
cd frontend/
npx railway service  # Select "frontend"
```

2. Configure Railway service settings (via Railway dashboard):
- Go to Settings → Root Directory
-   Set to: frontend

3. Set environment variables (via Railway dashboard):
`VITE_API_URL=https://your-backend-domain.up.railway.app`

4. Deploy:
```bash
npx railway up
npx railway domain
```