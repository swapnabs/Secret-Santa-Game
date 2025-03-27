import React, { useState } from "react";
import axios from "axios";
import { Container, Typography, TextField, Button, CircularProgress, Alert, Stack } from "@mui/material";

function SecretSantaUpload() {
  const [employeeFile, setEmployeeFile] = useState(null);
  const [previousAssignmentsFile, setPreviousAssignmentsFile] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [downloadUrl, setDownloadUrl] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);

  // Handle file input changes
  const handleEmployeeFileChange = (event) => {
    setEmployeeFile(event.target.files[0]);
  };

  const handlePreviousAssignmentsFileChange = (event) => {
    setPreviousAssignmentsFile(event.target.files[0]);
  };

  // Handle form submission
  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!employeeFile || !previousAssignmentsFile) {
      setErrorMessage("Please upload both files.");
      return;
    }

    setIsLoading(true);
    setErrorMessage(null);

    const formData = new FormData();
    formData.append("employeesFile", employeeFile);
    formData.append("previousAssignmentsFile", previousAssignmentsFile);

    try {
      const response = await axios.post("http://localhost:8080/api/secretsanta/assign", formData, {
        responseType: "blob", // To handle binary data
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      // Create a download URL for the resulting file
      const url = window.URL.createObjectURL(new Blob([response.data]));
      setDownloadUrl(url);
    } catch (error) {
      setErrorMessage("An error occurred while processing the files.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <Container maxWidth="sm" sx={{ padding: "2rem", backgroundColor: "#f5f5f5", borderRadius: "8px" }}>
      <Typography variant="h4" align="center" gutterBottom>
      Secret Santa Game 
      </Typography>
        <Stack spacing={2}>
          <Typography>Employee Data (.xlsx)</Typography>
          <TextField
            type="file"
            inputProps={{ accept: ".xlsx" }}
            fullWidth
            onChange={handleEmployeeFileChange}
            variant="outlined"
          />
          <Typography>Previous Assignments (.xlsx)</Typography>
          <TextField
            type="file"
            inputProps={{ accept: ".xlsx" }}
            fullWidth
            onChange={handlePreviousAssignmentsFileChange}
            variant="outlined"
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            disabled={isLoading}
            sx={{ padding: "1rem" }}
            onClick={handleSubmit}
          >
            {isLoading ? <CircularProgress size={24} color="inherit" /> : "Generate Assignments"}
          </Button>
        </Stack>

      {errorMessage && (
        <Alert severity="error" sx={{ marginTop: "1rem" }}>
          {errorMessage}
        </Alert>
      )}

      {downloadUrl && (
        <Stack direction="row" justifyContent="center" sx={{ marginTop: "1rem" }}>
          <Button variant="outlined" color="success" href={downloadUrl} download="secret_santa_assignments.xlsx">
            Click here to download your assignments
          </Button>
        </Stack>
      )}
    </Container>
  );
}

export default SecretSantaUpload;
