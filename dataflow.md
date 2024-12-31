
flowchart TD
A[Customer makes a purchase] --> B[Transaction is created with amount and date]
B --> C[Transaction is saved to the database]
C --> D[Customer details are updated with new transaction]
D --> E[Reward points calculation triggered based on transaction amount]
E --> F{Is the amount > $100?}
F -->|Yes| G[2 points for each dollar > $100]
F -->|No| H{Is the amount > $50?}
H -->|Yes| I[1 point for each dollar > $50]
H -->|No| J[No reward points if amount <= $50]
G --> K[Reward points are added to the customer total]
I --> K
J --> K
K --> L[Monthly points are calculated based on transaction date]
L --> M[Total points for the customer are aggregated]
M --> N[API endpoint for calculating reward points per customer and time range]
N --> O[User inputs start date and end date for reward calculation]
O --> P[Transaction records are filtered by date]
P --> Q[Points for each transaction are calculated and aggregated]
Q --> R[Response with customer details and reward points is returned]
R --> S[Reward points information is sent back to the user]