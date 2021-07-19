import React from 'react';
import axios from 'axios';
import './contact.css'

export default class Contact extends React.Component {
    state = {
        userList: [],
    }

    fetchUserList() {
        axios.get('https://jsonplaceholder.typicode.com/users')
        .then((response) => {
            if (response.status === 200) {
                this.setState({userList : response.data});
            }
            
        });
    }

    componentDidMount () {
        this.fetchUserList();
    }

    handleOnDelete(userID) {
        axios.delete('https://jsonplaceholder.typicode.com/users/'+{userID})
        .then((response) => {
            this.setState({userList: this.state.userList.splice()});
            //this.fetchUserList();
        });
    }
    
    render () {
        return (
            <table id="table">
                <thead>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th></th>
                </thead>
                <tbody>
                    {this.state.userList.map((user) => (
                        <tr key={user.id}>
                            <td>{user.id}</td>
                            <td>{user.name}</td>
                            <td>{user.email}</td>
                            <td>
                                <button onClick={() => this.handleOnDelete()}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        );
    }
}