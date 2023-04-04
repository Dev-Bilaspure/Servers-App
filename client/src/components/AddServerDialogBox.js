import React, { useContext, useState } from "react";
import Dialog from "@mui/material/Dialog";
import { Button, LinearProgress, TextField, Typography } from "@mui/material";
import axios from "axios";
import { ServerContext } from "../contexts/ServerContext";

const AddServerDialog = ({ handleDialogBoxClose, openDialogBox }) => {
  const { addNewServer } = useContext(ServerContext);
  const [name, setName] = useState("");
  const [language, setLanguage] = useState("");
  const [framework, setFramework] = useState("");

  const [isFetching, setIsFetching] = useState(false);

  const handleSubmit = async () => {
    if (name === "" || language === "" || framework === "") return;
    try {
      setIsFetching(true);
      await axios
        .post("http://localhost:8080/api/servers", {
          name,
          language,
          framework,
        })
        .then((res) => {
          addNewServer(res.data);
          setIsFetching(false);
          setName("");
          setFramework("");
          setLanguage("");
          handleDialogBoxClose();
        })
        .catch((err) => {
          setIsFetching(false);
          console.log(err);
        });
    } catch (error) {
      setIsFetching(false);
      console.log(error);
    }
  };
  return (
    <div>
      <Dialog onClose={handleDialogBoxClose} open={openDialogBox}>
        <div style={{ width: 400, padding: 20, paddingRight: 20 }}>
          <div
            style={{ width: "fit-content", margin: "auto", marginBottom: 20 }}
          >
            <Typography
              style={{
                fontFamily: `'Roboto', 'sans-serif'`,
                color: "rgb(30, 30, 30)",
                fontSize: 25,
                fontWeight: "bold",
              }}
            >
              Add Server
            </Typography>
          </div>
          <div>
            <Typography
              style={{
                fontFamily: `'Roboto', 'sans-serif'`,
                color: "rgb(30, 30, 30)",
                fontSize: 17,
              }}
            >
              Name
            </Typography>
            <TextField
              variant="outlined"
              size="small"
              placeholder="Name"
              style={{ width: "100%" }}
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
            />
          </div>
          <div style={{ marginTop: 20 }}>
            <Typography
              style={{
                fontFamily: `'Roboto', 'sans-serif'`,
                color: "rgb(30, 30, 30)",
                fontSize: 17,
              }}
            >
              Language
            </Typography>
            <TextField
              variant="outlined"
              size="small"
              placeholder="Name"
              style={{ width: "100%" }}
              value={language}
              onChange={(e) => {
                setLanguage(e.target.value);
              }}
            />
          </div>
          <div style={{ marginTop: 20 }}>
            <Typography
              style={{
                fontFamily: `'Roboto', 'sans-serif'`,
                color: "rgb(30, 30, 30)",
                fontSize: 17,
              }}
            >
              Framework
            </Typography>
            <TextField
              variant="outlined"
              size="small"
              placeholder="Name"
              style={{ width: "100%" }}
              value={framework}
              onChange={(e) => {
                setFramework(e.target.value);
              }}
            />
          </div>
          <div style={{ width: "100%", marginTop: 30, marginBottom: 10 }}>
            <Button
              onClick={handleSubmit}
              variant="contained"
              style={{
                background: "rgb(80, 80, 80)",
                textTransform: "none",
                margin: "auto",
                width: "100%",
              }}
            >
              <Typography>Submit</Typography>
            </Button>
          </div>
        </div>
        {isFetching && <LinearProgress color="inherit" />}
      </Dialog>
    </div>
  );
};

export default AddServerDialog;
