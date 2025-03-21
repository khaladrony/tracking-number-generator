# Tracking Number Generator API

A RESTful API that generates unique tracking numbers for parcels. This API is scalable, efficient, and capable of handling high concurrency.

## API Design Doc
[View PDF](Tracking_Number_Generator_API.pdf)

## Features

- Generates unique tracking numbers matching the regex pattern: `^[A-Z0-9]{1,16}$`
- Accepts various parameters for customization
- Uses Redis for ensuring uniqueness across multiple instances
- Horizontally scalable design

## Tech Stack

- Java 17
- Spring Boot 3.2.4
- Redis for distributed uniqueness checking
- Docker for containerization
- Spring Actuator for monitoring

## API Specification

### GET /next-tracking-number

Generates a new unique tracking number.

#### Query Parameters

| Parameter | Description | Format | Example |
|-----------|-------------|--------|---------|
| origin_country_id | Origin country code | ISO 3166-1 alpha-2 | "MY" |
| destination_country_id | Destination country code | ISO 3166-1 alpha-2 | "ID" |
| weight | Order weight in kg | Up to 3 decimal places | "1.234" |
| created_at | Order creation timestamp | RFC 3339 | "2018-11-20T19:29:32+08:00" |
| customer_id | Customer UUID | UUID | "de619854-b59b-425e-9db4-943979e1bd49" |
| customer_name | Customer name | String | "RedBox Logistics" |
| customer_slug | Customer name in slug-case | String | "redbox-logistics" |

#### Response

```json
{
  "tracking_number": "MYID1A2B3C4D5E6F",
  "created_at": "2023-03-19T12:34:56+08:00",
  "origin_country_id": "MY",
  "destination_country_id": "ID"
}
```

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven
- Docker (for Redis and containerization)

### Local Development Setup

1. Clone the repository
```bash
git clone https://github.com/username/tracking-number-generator.git
cd tracking-number-generator
```

2. Start Redis with Docker
```bash
docker run --name redis -p 6379:6379 -d redis
```

3. Build the application
```bash
mvn clean package
```

4. Run the application
```bash
java -jar target/tracking-number-generator-0.0.1-SNAPSHOT.jar
```

5. Access the API at http://localhost:8080/next-tracking-number

### Docker Deployment

1. Build the Docker image
```bash
docker build -t tracking-number-generator .
```

2. Run the application with Redis
```bash
docker-compose up -d
```

## Testing

Run the automated tests with:
```bash
mvn test
```

## Deployment

For production deployment, you can use:

1. **Docker Swarm or Kubernetes**: For container orchestration
2. **AWS Elastic Beanstalk**: For simplified deployment
3. **Google Cloud Run**: For serverless deployment

## Scaling Strategy

- Horizontal scaling: Run multiple instances behind a load balancer
- Redis ensures uniqueness across instances
- Stateless design allows for easy scaling

## Monitoring

Use Spring Boot Actuator endpoints for monitoring:

- Health check: `/actuator/health`
- Metrics: `/actuator/metrics`
- Info: `/actuator/info`

## Example Usage

```bash
curl "http://localhost:8080/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2023-03-19T12:34:56%2B08:00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics"
```