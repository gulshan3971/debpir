cli = Client(net_profile="network_config/network.yaml")
cli.chaincode_invoke("DEBPIRContract", "PrivacyLabeling", ["data1", [5, 8], [3, 4]])
