import './App.css';
import React from 'react';
import HomePage from "./Page/HomePage/homepage"
import NavBar from "./Components/NavBar/navbar"
import Contact from "./Components/Contact/contact"
import {Route, BrowserRouter, Switch} from "react-router-dom"

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
      <BrowserRouter>
        <div className="root">
          <NavBar/>
          <SuperHeroBanner/>
          <SideBar/>

          <Switch>
            <Route exact path="/home">
              <HomePage name={this.state.name} bootcamp={this.state.bootcamp}/>
            </Route>
            <Route exact path="/contact">
              <Contact></Contact>
            </Route>
          </Switch>

          <Footer></Footer>/
        </div>
      </BrowserRouter>
    );
  }
}

export default App;