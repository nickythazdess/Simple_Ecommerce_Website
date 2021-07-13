import logo from './logo.svg';
import './App.css';
import React, {useState} from 'react';
import HomePage from './Page/HomePage'
import NavBar from './Component/NavBar'

class App extends React.Component {
  state = {
    name: "Tan",
    bootcamp: 'Rookies',
  };

  async updateName(newName) {
    await this.setState({ name : newName });
    console.log(this.state.name);
    //setTimeOut(() => {console.log(this.state.name), 0}); // async
    //this.setState({ name : newName }, () => console.log(this.state.name)); //callback
  }

  render () {
    
    return (
      <div className="App">
        <NavBar name={this.state.name}/>
        <HomePage name={this.state.name} bootcamp={this.state.bootcamp}/>

        <button onClick={() => this.updateName("New name")}>UpdateName</button>
      </div>
    );
  }
}

export default App;