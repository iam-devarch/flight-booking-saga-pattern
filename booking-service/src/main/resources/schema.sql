DROP TABLE IF EXISTS FLIGHT_BOOKING;
CREATE TABLE FLIGHT_BOOKING (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    passenger_id NUMERIC(10),
    pnr_number VARCHAR(50),
    flight_number VARCHAR(50),
    price NUMERIC(10, 2),
    status VARCHAR(50),
    booked_seats NUMERIC(10)
);