import React, { useState } from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import AddIcon from "@mui/icons-material/Add";
import { Button, Typography } from "@mui/material";
import AddServerDialog from "./AddServerDialogBox";

const Navbar = () => {
  const [openDialogBox, setOpenDialogBox] = useState(false);
  const handleDialogBoxClose = () => {
    setOpenDialogBox(false);
  };
  const handleDialogBoxOpen = () => {
    setOpenDialogBox(true);
  };

  return (
    <div>
      <AppBar
        position="fixed"
        style={{
          boxShadow: "none",
          borderBottom: "1px solid #000000",
          background: "#fff",
          height: 65,
        }}
      >
        <Toolbar>
          <div style={{ display: "flex", width: "100%" }}>
            <div>
              <Typography
                style={{
                  fontWeight: "bold",
                  fontFamily: `'Roboto', 'sans-serif'`,
                  color: "rgb(60, 60, 60)",
                  fontSize: 26,
                }}
              >
                ServerVaultServers
              </Typography>
            </div>
          </div>
          <div style={{ marginLeft: "auto", paddingTop: 0 }}>
            <Button
              variant="contained"
              style={{
                background: "rgb(70, 70, 70)",
                textTransform: "none",
                width: 130,
              }}
              onClick={handleDialogBoxOpen}
            >
              <AddIcon />
              Add Server
            </Button>
          </div>
        </Toolbar>
      </AppBar>
      <AddServerDialog
        handleDialogBoxClose={handleDialogBoxClose}
        openDialogBox={openDialogBox}
      />
    </div>
  );
};

export default Navbar;
