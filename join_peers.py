cli = Client(net_profile="network_config/network.yaml")
cli.new_channel("debpirchannel")
cli.peer_join_channel("peer0.org1.example.com", "debpirchannel")
