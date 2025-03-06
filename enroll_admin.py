from hfc.fabric import Client

cli = Client(net_profile="network_config/network.yaml")
cli.new_channel("debpirchannel")
cli.enroll("Admin", "adminpw", "ca.org1.example.com")
