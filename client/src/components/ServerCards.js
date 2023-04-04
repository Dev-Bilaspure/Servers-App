import { Grid } from "@mui/material";
import React, { useContext } from "react";
import { ServerContext } from "../contexts/ServerContext";
import ServerCard from "./ServerCard";

const ServerCards = () => {
  const { servers } = useContext(ServerContext);
  return (
    <div>
      <Grid container spacing={5}>
        {servers.map((shp) => {
          return (
            <Grid item key={shp._id} lg={3} md={4} sm={6} xs={12}>
              <ServerCard
                name={shp.name}
                language={shp.language}
                framework={shp.framework}
                serverId={shp.serverId}
              />
            </Grid>
          );
        })}
      </Grid>
    </div>
  );
};

export default ServerCards;
