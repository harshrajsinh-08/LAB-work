# Given values
propagation_speed = 2 * 10**8  # m/s

# Data for each datagram
datagrams = {
    1: {'path_length_km': 3200, 'switching_delays_ms': [3, 20, 20]},
    2: {'path_length_km': 11700, 'switching_delays_ms': [3, 10, 20]},
    3: {'path_length_km': 12200, 'switching_delays_ms': [3, 10, 20, 20]},
    4: {'path_length_km': 10200, 'switching_delays_ms': [3, 7, 20]},
    5: {'path_length_km': 10700, 'switching_delays_ms': [3, 7, 20, 20]},
}

# Function to calculate total delay for each datagram
def calculate_total_delay(datagram):
    # Convert path length to meters
    path_length_m = datagram['path_length_km'] * 1000
    
    # Propagation delay in seconds
    propagation_delay_s = path_length_m / propagation_speed
    
    # Propagation delay in ms (1s = 1000ms)
    propagation_delay_ms = propagation_delay_s * 1000
    
    # Total switching delay in ms
    total_switching_delay_ms = sum(datagram['switching_delays_ms'])
    
    # Total delay in ms
    total_delay_ms = propagation_delay_ms + total_switching_delay_ms
    
    return total_delay_ms

# Calculate the total delay for each datagram
total_delays = {datagram_id: calculate_total_delay(data) for datagram_id, data in datagrams.items()}

# Sort the datagrams by the total delay to find the order of arrival
sorted_delays = sorted(total_delays.items(), key=lambda item: item[1])

total_delays, sorted_delays