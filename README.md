# visualizing-healthy-lives-api

# Updating AWS EC2 Instance To Allow Larger File Size

1. SSH into instance 
2. `sudo nano /etc/nginx/nginx.conf`
3. Add the following lines under "server":
`client_header_timeout 120;`
`client_body_timeout   120;`     
`keepalive_timeout     120;`     
`client_max_body_size  15M;`
4. `service nginx restart  `
 
