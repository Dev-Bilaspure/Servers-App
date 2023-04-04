import Navbar from "./components/Navbar";
import ServerCards from "./components/ServerCards";

const App = () => {
  return (
    <div className="App">
      <Navbar />
      <div style={{ marginTop: 100, padding: 20 }}>
        <ServerCards />
      </div>
    </div>
  );
};

export default App;
