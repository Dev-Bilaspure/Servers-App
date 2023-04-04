import { Button, Paper, Typography } from "@mui/material";
import React, { useContext } from "react";
import axios from "axios";
import { ServerContext } from "../contexts/ServerContext";

const ServerCard = ({ name, language, framework, serverId }) => {
  const { removeServer } = useContext(ServerContext);

  const handleDeleteServer = async () => {
    const ans = window.confirm("Are you sure you want to delete this server?");
    if (!ans) return;
    try {
      await axios
        .delete(`http://localhost:8080/api/servers/${serverId}`)
        .then((res) => {
          removeServer(serverId);
        })
        .catch((err) => {
          console.log(err);
        });
    } catch (error) {
      console.log(error);
    }
  };
  return (
    <div style={{ width: "100%" }}>
      <Paper
        elevation={2}
        style={{ width: "100%", paddingTop: 15, paddingBottom: 20 }}
      >
        <div style={{ width: "fit-content", margin: "auto", marginBottom: 20 }}>
          <Typography
            style={{
              fontFamily: `'Roboto', 'sans-serif'`,
              fontSize: 22,
              fontWeight: "bold",
              borderBottom: "1px solid rgb(60, 60, 60)",
              paddingBottom: 3,
            }}
          >
            {name.charAt(0).toUpperCase() + name.slice(1)}
          </Typography>
        </div>
        <div style={{ display: "flex", width: "fit-content", margin: "auto" }}>
          <Typography style={{ fontWeight: "bold", fontSize: 18 }}>
            Langage:
          </Typography>
          <Typography style={{ marginLeft: 20, fontSize: 18 }}>
            {language.charAt(0).toUpperCase() + language.slice(1)}
          </Typography>
        </div>
        <div
          style={{
            display: "flex",
            width: "fit-content",
            margin: "auto",
            marginTop: 10,
          }}
        >
          <Typography style={{ fontWeight: "bold", fontSize: 18 }}>
            Framework:
          </Typography>
          <Typography style={{ marginLeft: 20, fontSize: 18 }}>
            {framework.charAt(0).toUpperCase() + framework.slice(1)}
          </Typography>
        </div>
        <div style={{ width: 70, margin: "auto", marginTop: 20 }}>
          <Button
            variant="contained"
            onClick={handleDeleteServer}
            style={{
              background: "rgb(214, 214, 214)",
              textTransform: "none",
              margin: "auto",
              width: "100%",
              color: "rgb(247, 2, 2)",
              boxShadow: "none",
            }}
          >
            Delete
          </Button>
        </div>
      </Paper>
    </div>
  );
};

export default ServerCard;
